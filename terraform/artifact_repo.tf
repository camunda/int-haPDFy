resource "google_artifact_registry_repository" "hapdfy" {
  location      = local.region
  repository_id = "hapdfy"
  description   = "Stores the haPDFy images"
  format        = "DOCKER"
  depends_on = [ google_project_service.services ]
}

resource "google_artifact_registry_repository_iam_member" "member" {
  project = google_artifact_registry_repository.hapdfy.project
  location = google_artifact_registry_repository.hapdfy.location
  repository = google_artifact_registry_repository.hapdfy.name
  role = "roles/artifactregistry.writer"
  member = local.gha_service_account
}