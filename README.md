# Nova Manufacturing Spring Boot Examination Starter

Starter project for the **Nova Manufacturing Employee, Production, Sales and Payroll Management System** practical examination.

The examination is six hours, carries 100 marks, and requires Java 21, Spring Boot, Spring Data JPA, Jakarta Bean Validation, and MySQL or PostgreSQL.

## Run the application

```bash
mvn spring-boot:run
```

The current configuration uses:

```text
Port: 9090
Context path: /nova-service
Database: MySQL by default
```

Set database credentials through environment variables:

```bash
export DB_USERNAME=root
export DB_PASSWORD=your-password
```

To use PostgreSQL, change `spring.profiles.active` to `postgresql` in
`src/main/resources/application.properties`.

The application expects a database named `pos_db`. See the profile files:

- `src/main/resources/application-mysql.properties`
- `src/main/resources/application-postgresql.properties`

## Examination objective

Build a REST backend for Nova Manufacturing (Pvt) Ltd. The system manages employees, salary revisions, projects, production batches, sales, incentives, bonuses, payroll, and a management dashboard.

Only the following six graded endpoints are required. Department, project, and product CRUD endpoints are not required.

| # | Method | Endpoint | Marks |
|---|---|---|---:|
| 1 | POST | `/api/employees` | 15 |
| 2 | PATCH | `/api/employees/{id}/salary` | 10 |
| 3 | POST | `/api/production` | 15 |
| 4 | POST | `/api/sales` | 20 |
| 5 | POST | `/api/payroll/calculate` | 25 |
| 6 | GET | `/api/dashboard?year=YYYY&month=M` | 15 |

The starter `TestController` is only a development/sample controller. It is not one of the six examination APIs.

## Endpoint requirements

### 1. Register employee

`POST /api/employees`

Accept employee code, name, email, joining date, department, designation, optional supervisor, basic salary, and project assignments containing project identifiers and allocation percentages.

Rules include:

- Department and projects must exist; department must be active.
- Employee code and email must be unique.
- Basic salary must be positive.
- Project assignments must not repeat a project.
- Each allocation must be greater than zero and total allocation must not exceed 100%.
- A supervisor must be an active employee in the same department with `SUPERVISOR` or `MANAGER` designation.
- Employee and assignments must be saved atomically.

### 2. Revise employee salary

`PATCH /api/employees/{id}/salary`

Accept a new positive basic salary and revision reason. Reject unknown employees, apply the revision date on the request date, preserve the previous salary, and save the employee update and salary history atomically.

### 3. Record production

`POST /api/production`

Accept product, production date, quantity produced, material cost, direct labour cost, other cost, and production supervisor.

- Supervisor must be an active Production employee with `SUPERVISOR` or `MANAGER` designation.
- Quantity must be greater than zero.
- Costs cannot be negative.
- `total production cost = material cost + labour cost + other cost`.
- `unit production cost = total production cost / quantity produced`.
- Persist calculated costs and initialize remaining stock to produced quantity.

### 4. Record sale

`POST /api/sales`

Accept production batch, sales employee, sale date, quantity sold, and actual unit selling price.

- Only an active Sales employee may sell.
- Unit price and quantity must be positive.
- Quantity must not exceed remaining batch inventory.
- `revenue = quantity sold x unit selling price`.
- `COGS = quantity sold x stored batch unit cost`.
- `gross profit = revenue - COGS`.
- Persist the sale and reduce batch inventory atomically.
- Protect against concurrent sales overselling a batch.
- No cancellation endpoint is required.

### 5. Finalize monthly payroll

`POST /api/payroll/calculate`

Accept a year and month. Only a completed calendar month is valid. The endpoint must calculate and persist employee sales incentives, the company bonus pool, individual bonuses, and final payroll in one transaction. Repeating an already finalized month must be rejected.

Progressive employee sales incentive rates:

| Completed monthly sales revenue slice | Rate |
|---|---:|
| First LKR 500,000 | 2% |
| Next LKR 500,000 | 3% |
| Next LKR 1,000,000 | 5% |
| Above LKR 2,000,000 | 7% |

Company bonus pool based on gross margin:

| Gross margin | Bonus pool |
|---|---:|
| Below 10% | 0% of revenue |
| 10% to below 20% | 2% of revenue |
| 20% to below 30% | 4% of revenue |
| 30% and above | 6% of revenue |

Designation weights:

| Designation | Weight |
|---|---:|
| MANAGER | 3.0 |
| SUPERVISOR | 2.0 |
| SENIOR_EMPLOYEE | 1.5 |
| EMPLOYEE | 1.0 |

Individual bonus is calculated from the designation weight, total eligible weight, and bonus pool. Round pool and payments to two decimal places using the specified largest-remainder distribution rule so distributed bonuses equal the rounded pool exactly.

Final salary is:

```text
gross monthly salary = basic salary + sales incentive + company bonus
```

### 6. Management dashboard

`GET /api/dashboard?year=YYYY&month=M`

Return current and selected-month information, including employee counts and salary totals by department, production quantity and cost, units sold, revenue, COGS, gross profit, current inventory, monthly bonus pool, finalized incentives and payroll when available, and the top three sales employees ranked by monthly revenue.

Use database aggregates where appropriate and clearly distinguish current snapshots from period-specific values.

## Database reference

The reference document provides suggested attributes. Students must independently design primary keys, foreign keys, uniqueness rules, indexes, relationship cardinality, ownership, and JPA mappings.

Required business tables and suggested attributes:

| Table | Suggested attributes |
|---|---|
| Department | `department_id`, `department_code`, `department_name`, `description`, `status` |
| Employee | `employee_id`, `employee_code`, `full_name`, `email`, `phone_number`, `joined_date`, `department_id`, `designation`, `supervisor_id`, `basic_salary`, `initial_basic_salary`, `status` |
| Project | `project_id`, `project_code`, `project_name`, `project_type`, `start_date`, `end_date`, `status` |
| Employee Project Assignment | `assignment_id`, `employee_id`, `project_id`, `allocation_percentage`, `assigned_date` |
| Salary History | `salary_history_id`, `employee_id`, `previous_salary`, `new_salary`, `effective_date`, `reason` |
| Product | `product_id`, `product_code`, `product_name`, `description`, `default_selling_price`, `status` |
| Production Batch | `batch_id`, `batch_code`, `product_id`, `production_date`, `quantity_produced`, `remaining_quantity`, `material_cost`, `labor_cost`, `other_cost`, `total_production_cost`, `unit_production_cost`, `production_supervisor_id` |
| Sale | `sale_id`, `batch_id`, `sales_employee_id`, `sale_date`, `quantity_sold`, `unit_selling_price`, `total_revenue`, `cost_of_goods_sold`, `gross_profit` |
| Monthly Payroll | `payroll_id`, `employee_id`, `payroll_year`, `payroll_month`, `basic_salary`, `monthly_sales_revenue`, `sales_incentive`, `company_bonus`, `gross_salary`, `calculated_at`, `status` |
| Monthly Payroll Summary | `summary_id`, `payroll_year`, `payroll_month`, `monthly_revenue`, `monthly_cogs`, `monthly_gross_profit`, `gross_margin_percentage`, `bonus_pool`, `total_sales_incentives`, `total_gross_payroll`, `status`, `calculated_at` |

## Reference master data

Departments:

```text
PROD  - Production       - ACTIVE
SALES - Sales            - ACTIVE
ADMIN - Administration   - ACTIVE
FIN   - Finance          - ACTIVE
```

Projects:

```text
PRD-001  Assembly Line Optimization  PRODUCTION  ACTIVE
PRD-002  Packaging Improvement       PRODUCTION  ACTIVE
SAL-001  Regional Sales Growth       SALES       ACTIVE
SAL-002  Corporate Client Outreach   SALES       ACTIVE
```

Products:

```text
P-100  Storage Box        LKR 1,200  ACTIVE
P-200  Desk Organizer     LKR   850  ACTIVE
P-300  Plastic Container  LKR   650  ACTIVE
P-400  Packaging Tray     LKR   500  ACTIVE
P-500  Utility Basket     LKR 1,450  ACTIVE
```

Initial employees include four active reference managers/supervisors:

```text
EMP-M001  Amara Perera       PROD   MANAGER     150,000
EMP-M002  Nimal Fernando     SALES  MANAGER     160,000
EMP-S001  Kavindi Silva      PROD   SUPERVISOR  110,000
EMP-S002  Ruwan Jayasinghe   SALES  SUPERVISOR  115,000
```

Allowed/reference values:

- Department and employee status: `ACTIVE`, `INACTIVE`
- Designation: `MANAGER`, `SUPERVISOR`, `SENIOR_EMPLOYEE`, `EMPLOYEE`
- Project type: `PRODUCTION`, `SALES`
- Project status: `PLANNED`, `ACTIVE`, `COMPLETED`
- Payroll status: `FINALIZED`
- Allocation percentage: greater than 0 and at most 100
- Money: exact decimal values with two decimal places
- Production and sales quantities: positive integers

## Technical rules

- Use Java 21, Spring Boot, Spring Data JPA, and MySQL or PostgreSQL.
- Use controller/service/repository separation and DTOs.
- Keep business logic in service classes.
- Use centralized exception handling.
- Use `BigDecimal` or equivalent exact decimal types for money.
- Store monetary values at two decimal places and use `HALF_UP` where ordinary financial rounding is required.
- All responses must use JSON with `status` (`code`, `message`) and `data`; HTTP status and status code must agree.
- Return validation errors as 400, not-found errors as 404, and conflicts such as duplicate finalization or uniqueness violations as 409.
- Do not implement frontend, authentication, authorization, logging, or additional exam endpoints.

## Submission requirements

Submit:

- Source project
- Database schema
- Simple entity-relationship diagram
- Seed/master data
- Sample transactions
- Postman collection containing requests and example responses for all six endpoints

The examiner will test both successful and rejected operations.

## Suggested six-hour schedule

| Activity | Minutes |
|---|---:|
| Database and setup | 55 |
| Employee registration | 45 |
| Salary revision | 25 |
| Production | 40 |
| Sales and inventory | 55 |
| Monthly payroll | 85 |
| Dashboard and testing | 55 |
| **Total** | **360** |
