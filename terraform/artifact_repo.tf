resource "google_artifact_registry_repository" "hapdfy" {
  location      = var.region
  repository_id = "hapdfy"
  description   = "Stores the haPDFy images"
  format        = "DOCKER"
}

resource "google_artifact_registry_repository_iam_member" "member" {
  project = google_artifact_registry_repository.hapdfy.project
  location = google_artifact_registry_repository.hapdfy.location
  repository = google_artifact_registry_repository.hapdfy.name
  role = "roles/artifactregistry.writer"
  member = var.gha_service_account
}