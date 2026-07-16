# Terraform for claims-agent — DELIBERATELY INSECURE fixture IaC.
# Fake account/ARNs only. Do not apply.
#
# Guard: an impossible required_version makes `terraform apply` fail closed, so
# this can never be provisioned by accident. Static scanners still parse the HCL.
terraform {
  required_version = ">= 999.0.0"
}

provider "aws" {
  region  = "us-east-1"
  profile = "fixture-fake-account"
}

# VULN: public claims-document bucket (CWE-732).
resource "aws_s3_bucket" "claim_docs" {
  bucket = "claims-agent-docs-fixture"
}

resource "aws_s3_bucket_public_access_block" "claim_docs" {
  bucket                  = aws_s3_bucket.claim_docs.id
  block_public_acls       = false
  block_public_policy     = false
  ignore_public_acls      = false
  restrict_public_buckets = false
}

# VULN: unencrypted, public RDS with committed weak password (CWE-798).
resource "aws_db_instance" "claims" {
  identifier          = "claims-agent-fixture"
  engine              = "postgres"
  instance_class      = "db.t3.micro"
  allocated_storage   = 20
  username            = "claims"
  password            = "claims-CHANGEME-committed-1357"
  storage_encrypted   = false
  publicly_accessible = true
  skip_final_snapshot = true
}

# VULN: over-broad IAM (CWE-284).
resource "aws_iam_role_policy" "claims_service" {
  name = "claims-service-allow-all"
  role = "claims-agent-service-fixture"
  policy = jsonencode({
    Version = "2012-10-17"
    Statement = [{
      Effect   = "Allow"
      Action   = "*"
      Resource = "*"
    }]
  })
}
