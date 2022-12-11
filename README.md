# wikipedia
Get short description of a person/username.

Clone the repo and checkout to the main branch.

How to Run the project:

1. Navigate to the project folder and run mvn spring-boot:run  to run the project which will start on port 8085.

2. Hit the get endpoint "http://localhost:8085/api/person/description" with the username as a query param e.g for john cena this will be the endpoint to hit
"http://localhost:8085/api/person/description?Username=john cena"

Alternatively run the dockerfile to generate a docker container which will start on port 8085, repeat step 2 to test your API.
