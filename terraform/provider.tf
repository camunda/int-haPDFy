terraform {
  required_version = "~> 1.2"

  required_providers {
    google = {
      source  = "hashicorp/google"
      version = "5.7.0"
    }
  }
}

provider "google" {
  project = var.google_project_id
  region  = local.region
}