GRANT CREATE ON DATABASE issuingbank TO lesson;
CREATE SCHEMA IF NOT EXISTS issuingbankschema;

-- Настройка прав
GRANT ALL PRIVILEGES ON SCHEMA issuingbankschema TO lesson;

-- Установка search_path (необязательно, но полезно)
SET search_path TO issuingbankschema;


