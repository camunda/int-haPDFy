resource "google_service_account" "hapdfy" {
  account_id   = "hapdfy-sa"
  display_name = "SA for the haPDFy service"
  description  = "The service account for the web service"
}
resource "google_project_iam_member" "hapdfy" {
  for_each = toset([
    "roles/secretmanager.secretAccessor",
    "roles/logging.logWriter"
  ])

  project = var.google_project_id
  role    = each.key
  member  = "serviceAccount:${google_service_account.hapdfy.email}"
}
