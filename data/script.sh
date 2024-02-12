#!/bin/bash

# Set location of psql command
PSQL="/usr/bin/psql"

# Set database connection details
HOST="localhost"
DATABASE="CakeStudio"
USER="user"
PORT="5432"

# Set location of SQL file
SQL_FILE="./Query_to_Database.sql"

# Execute psql command
"$PSQL" -h "$HOST" -d "$DATABASE" -U "$USER" -p "$PORT" -a -q -f "$SQL_FILE"

# Exit with status code
exit $?

