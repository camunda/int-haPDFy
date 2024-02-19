locals {
  image_url = "${var.region}-docker.pkg.dev/${var.google_project_id}/hapdfy/hapdfy@${var.image_digest}"
}

resource "google_cloud_run_service" "hapdfy" {
  depends_on = [
    google_project_iam_member.hapdfy
  ]
  name     = "${var.prefix}hapdfy"
  location = var.region

  metadata {
    annotations = {
      "run.googleapis.com/client-name" = "terraform"
      "run.googleapis.com/ingress"     = "all"
      "client.knative.dev/user-image"   = local.image_url
    }
  }

  template {
    spec {
      service_account_name = google_service_account.hapdfy.email
      containers {
        image = local.image_url
        resources {
          limits = {
            "cpu"    = "2000m",
            "memory" = "4Gi"
          }
        }
        env {
          name  = "spring_profiles_active"
          value = "default"
        }
        env {
          name = "HAPDFY_USERNAME"
          value = var.hapdfy_auth_config.username
        }
        env {
          name = "HAPDFY_PASSWORD"
          value_from {
            secret_key_ref {
              key  = var.hapdfy_auth_config.password_secret_version
              name = var.hapdfy_auth_config.password_secret_ref
            }
          }
        }
      }
    }
    metadata {
      annotations = {
        # Limit scale up to prevent any cost blow outs!
        "autoscaling.knative.dev/maxScale" = "2"
      }
    }
  }
}

data "google_iam_policy" "noauth" {
  binding {
    role    = "roles/run.invoker"
    members = ["allUsers"]
  }
}

resource "google_cloud_run_service_iam_policy" "noauth" {
  location = google_cloud_run_service.hapdfy.location
  project  = google_cloud_run_service.hapdfy.project
  service  = google_cloud_run_service.hapdfy.name

  policy_data = data.google_iam_policy.noauth.policy_data
}


output "url" {
  value = google_cloud_run_service.hapdfy.status[0].url
}
