# UserInterestApi

The User Interest Api Project implements REST API. The Rest API consists of the following
```
// Defines the controller class which consists of the following end points:

    GET:  /items: This end point will provide the list of all items available for the user.
    POST: /items/add: This end point will add an item to the existing item set. It accepts query parameters item id, name, type,
                       availability status and cost.
    DELETE: /items/remove/{id}: This end point will delete the item in the item set corresponding to the item id provided.
    GET: /items/request/{maxCost}: This end point will list the available items for the user based on the upper limit of cost provided.

// Uses H2 database for data persistence and retrieval

```

The testing methodology used involves the following:
```
1) Unit Testing by creating the simple mocks.
2) Integration Testing using MockMvc
3) Functional Testing using the RestAssured
```