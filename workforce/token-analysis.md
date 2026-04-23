# Step 1
{
"access_token": "eyJraWQiOiI2MDAzOWZmMC02MTg4LTRhYmQtOWJkMC1hOTllNDg2YzM5YjAiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJ3b3JrZm9yY2Utc2VydmljZSIsImF1ZCI6Indvcmtmb3JjZS1zZXJ2aWNlIiwibmJmIjoxNzc2MTk4NzQ5LCJzY29wZSI6WyJyZWFkOmVtcGxveWVlcyJdLCJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjkwMDAiLCJleHAiOjE3NzYyMDIzNDksImlhdCI6MTc3NjE5ODc0OSwianRpIjoiY2VhNWZmNWYtODI1MS00Yzg2LThlOGUtNDMwZjgxYzkyYWQ4In0.CAaIRlRnm66x49-wS8vUfKncMSXilzdnqxSmq6cyimtrisNXa2OhR-XnEowaw4iRc9r-LxnCTrDcINDWuW62Ech5ErlZV9NhTVNijHkygI49p31Jg5N3je_hyULGDyCY9u3YVohgEIp-oLvCkRWxkcMOcd7U3M_KKDxTx_SwcVB0p_F1ZlrlAVWcEli7S4-DzCkZGp3vsaGinX50Kzt9qtzRjk-MSJXeg4xmy4-fGxrpRPxxKmgwmy5klfzOJGotHNIz0QNX4RLgKCbH0WPWWx7lZCcWjdN9V5cKx-gqgxls_4iQMv2ErTF4o5JLxlzGgwbBpF11p02-U8aFWJBGYw",
"scope": "read:employees",
"token_type": "Bearer",
"expires_in": 3600
}
Response file saved.
> 2026-04-14T133229.200.json

Response code: 200; Time: 168ms (168 ms); Content length: 783 bytes (783 B)

# Step 2
GET http://localhost:8080/health

HTTP/1.1 200
X-Content-Type-Options: nosniff
X-XSS-Protection: 0
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Pragma: no-cache
Expires: 0
X-Frame-Options: DENY
Content-Type: application/json
Transfer-Encoding: chunked
Date: Tue, 14 Apr 2026 20:33:32 GMT

{
"status": "UP"
}
Response file saved.
> 2026-04-14T133332.200.json

Response code: 200; Time: 203ms (203 ms); Content length: 15 bytes (15 B)

# Step 3
GET http://localhost:8080/api/v1/employees

HTTP/1.1 401
WWW-Authenticate: Bearer
X-Content-Type-Options: nosniff
X-XSS-Protection: 0
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Pragma: no-cache
Expires: 0
X-Frame-Options: DENY
Content-Length: 0
Date: Tue, 14 Apr 2026 20:34:33 GMT

<Response body is empty>

Response code: 401; Time: 12ms (12 ms); Content length: 0 bytes (0 B)

# Step 4
GET http://localhost:8080/api/v1/employees

HTTP/1.1 200
X-Content-Type-Options: nosniff
X-XSS-Protection: 0
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Pragma: no-cache
Expires: 0
X-Frame-Options: DENY
Content-Type: application/json
Transfer-Encoding: chunked
Date: Tue, 14 Apr 2026 20:38:16 GMT

[
{
"id": "E001",
"name": "Alice Chen",
"department": "Engineering",
"role": "ENGINEER"
},
{
"id": "E002",
"name": "Bob Okafor",
"department": "Engineering",
"role": "SENIOR_ENGINEER"
},
{
"id": "E003",
"name": "Carol Diaz",
"department": "Human Resources",
"role": "HR_MANAGER"
}
]
Response file saved.
> 2026-04-14T133816.200.json

Response code: 200; Time: 276ms (276 ms); Content length: 251 bytes (251 B)

# Step 5
POST http://localhost:8080/api/v1/employees

HTTP/1.1 201
X-Content-Type-Options: nosniff
X-XSS-Protection: 0
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Pragma: no-cache
Expires: 0
X-Frame-Options: DENY
Content-Type: application/json
Transfer-Encoding: chunked
Date: Tue, 14 Apr 2026 20:39:28 GMT

{
"id": "E004",
"name": "Dana Park",
"department": "Finance",
"role": "ANALYST"
}
Response file saved.
> 2026-04-14T133928.201.json

Response code: 201; Time: 100ms (100 ms); Content length: 72 bytes (72 B)
