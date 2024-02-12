resource "google_project_service" "services" {
  for_each = toset([
    "artifactregistry.googleapis.com",
    "run.googleapis.com",
    "secretmanager.googleapis.com",
    # Manage permissions and service accounts
    "iam.googleapis.com",
    "iamcredentials.googleapis.com",
    # So GHA work
    "cloudresourcemanager.googleapis.com",
    "serviceusage.googleapis.com",
  ])
  project = var.google_project_id

  service = each.value

  disable_dependent_services = true

}