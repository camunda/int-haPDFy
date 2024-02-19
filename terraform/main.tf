locals {
  region              = "europe-north1"
  gha_service_account = "serviceAccount:${google_service_account.github_actions.email}"
}

module "prod" {
  source              = "./application"
  google_project_id   = var.google_project_id
  region              = local.region
  image_digest        = var.prod_image_digest
  gha_service_account = local.gha_service_account
  hapdfy_auth_config = {
    username                = "hapdfy-int-camunda-com"
    password_secret_ref     = "hapdfyPasswordSecret"
    password_secret_version = "latest"
  }
}
module "stage" {
  source              = "./application"
  google_project_id   = var.google_project_id
  region              = local.region
  image_digest        = var.stage_image_digest
  gha_service_account = local.gha_service_account
  prefix              = "stage-"
  hapdfy_auth_config = {
    username                = "hapdfy-int-camunda-com-stage"
    password_secret_ref     = "hapdfyPasswordSecretStage"
    password_secret_version = "latest"
  }
}


variable "google_project_id" {
  default = "hapdfy"
}

variable "prod_image_digest" {
  type     = string
  nullable = false
}
variable "stage_image_digest" {
  type     = string
  nullable = false
}

output "prod_url" {
  value = module.prod.url
}
output "stage_url" {
  value = module.stage.url
}

output "prod_image_digest" {
  value = var.prod_image_digest
}
output "stage_image_digest" {
  value = var.stage_image_digest
}

