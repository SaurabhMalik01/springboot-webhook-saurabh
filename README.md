# Spring Boot Webhook Submission - Bajaj Finserv JAVA

## 👤 Submitted by
- **Name**: Saurabh Malik
- **Email**: saurabh2269.be22@chitkara.edu.in

## 🚀 Description
This app:
- Sends a webhook registration request on startup
- Solves the SQL challenge
- Submits the final SQL query via JWT to the webhook

## 🔧 Tech Stack
- Spring Boot
- Java 17
- Maven
- RestTemplate

## 🧠 SQL Query
```sql
SELECT p.AMOUNT AS SALARY, CONCAT(e.FIRST_NAME, ' ', e.LAST_NAME) AS NAME,
TIMESTAMPDIFF(YEAR, e.DOB, CURDATE()) AS AGE, d.DEPARTMENT_NAME
FROM PAYMENTS p
JOIN EMPLOYEE e ON p.EMP_ID = e.EMP_ID
JOIN DEPARTMENT d ON e.DEPARTMENT = d.DEPARTMENT_ID
WHERE DAY(p.PAYMENT_TIME) != 1
ORDER BY p.AMOUNT DESC
LIMIT 1;
