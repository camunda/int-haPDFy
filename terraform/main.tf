locals {
  region = "europe-north1"
  gha_service_account = "serviceAccount:${google_service_account.github_actions.email}"  
}

module "prod" {
  source = "./application"
  google_project_id   = var.google_project_id
  region              = local.region
  image_digest        = var.prod_image_digest
  prefix              = ""
}
module "stage" {
  source = "./application"
  google_project_id   = var.google_project_id
  region              = local.region
  image_digest        = var.stage_image_digest
  prefix              = "stage-"
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

