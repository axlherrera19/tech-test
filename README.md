## Assumptions and technical considerations

### Business Logic Assumptions
- When calculating if a Player Profile is elegible for a running campaign, I assume **all** Campaing conditions must be matched, then I consider only player profiles who meet all campaign requirements **(AND logic)** must be associated to the campaign.  **If at least one condition doesn't match**, the user is not elegible for the campaign.
- For this example, I'm matching campaigns with player profiles on the fly. I assume no database updates to the Player Profile entity are requested for this excercise. For every request to this service, running campaigns are retrieved and checked against the user profile. **If the player profile matches all requirements, the campaign is assigned only to the result entity, and the modified player profile is returned, but nothing changes in the database.**



### Technical considerations
- I like to work with devcontainers, that's why I've created one. For booting this project without using devcontainers, you need to install JAVA 21 and Maven 3.9.X.
- I decided to create Campaign API anyway instead of mocking it, just to make the scenario a little bit more real. The endpoint `/api/campaign/get_running_campaigns` returns all Campaign entities from the Database. Each Campaign has a Matcher (DB entity too) assigned. The main application endpoint `/api/client/get_client_config/{player_profile_id}`, uses the Campaign API to retrieve all running campaigns.
- Despite this application would fit perfectly in a NonSQL database, this time I've chosen an in memory SQL database (H2) to avoid booting other databases with Docker and without installing any other software. *If it is needed, I don't have any problem to do another version of the excercide based on a NonSQL Database (Mongo, for instances).* 
- Database only lives whene application is up & running. The whole database is dropped and created back after each application restart.
- H2 console can be accessed via http://localhost:8080/h2-console
    + User: sa
    + Pass: (empty)
    + JDBC URL: jdbc:h2:mem:profile-matcher-db;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE
- I used Liquibase to create the whole DB schema and for preloading data for this example. It automatically run all scripts after application initialization.
- With Liquibase, I've created several campaigns, each which its own matcher, for testing purposes.
Only campaign with name `mycampaign` fully matches User profile. So, endpoint `/api/client/get_client_config/97983be2-98b7-11e7-90cf-082e5f28d836` is going to return only the `mycampaign` campaign within `activeCampaign`.
- **About Inventory**. As Inventory seems a dynamic map that contains the name of the item as "key", and the stock or quantity of that item as "value", and we are dealing with a SQL Database, I've created the Inventory column as a plain Text field that saves a JSON string. This JSON is transformed into a JAVA HashMap when fetching data from DB, making its management really easy from code perspective.
- Swagger available in http://localhost:8080/swagger-ui/index.html


## Usefull queries
```sql
SELECT * FROM PLAYER_PROFILE  p
inner join CLAN c on c.id = p.clan_id
inner join DEVICE d on d.player_id = p.player_id;
```


```sql
SELECT * from Campaign ca
inner join Matcher m on ca.matcher_id = m.id;
```

User Profile ID:
97983be2-98b7-11e7-90cf-082e5f28d836