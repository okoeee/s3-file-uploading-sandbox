
## Behavior when the same Path and same file name is uploaded to S3
Overwritten by the new file.
(bucket name, object key name) must be unique.

## Constraint of Database Table

```sql
CREATE TABLE uploaded_files (
  id INT AUTO_INCREMENT PRIMARY KEY,
  file_status VARCHAR(10) NOT NULL,
  CHECK (file_status IN ('uploaded', 'failed', 'pending'))
);
```