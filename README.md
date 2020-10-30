# Objective of The Project:
 This project built with a module which provide the services that matches our client’s need. When a customer made an application for a finance product, the service first provides authentication, authorization process. Then the whole service provide CRUD for both administrator users and client-side users.
# Technologies/Tools:
* Spring Boot
* Spring Cache
* Spring Data JPA
* Swagger2
* MySql
* Maven
* Junit
* JsonRpc
* ActiveMQ
* JPA
* RSA, TYK, HTTPS
* Quartz
* Postman
# UML Diagram
![alt text](https://github.com/nickee1942/finance-demo/blob/main/UML%20Diagram.jpeg)
# Database structure:
```javascript
*database manager
  table t_product(
  id varchar(50) not null comment 'product id',
  name varchar(50) not null comment 'product name',
  threshold_amount decimal(15,3) not null comment 'min amount',
  step_amount decimal(15,3) not null comment 'amount step',
  lock_term smallint not null comment 'lock term',
  reward_rate decimal(5,3) not null comment 'reward rate',
  status varchar(20) not null comment 'status',
  memo varchar(200) comment 'comment',
  create_at datetime comment 'create time',
  create_user varchar(20) comment 'create author',
  update_at datetime comment 'update time',
  update_user varchar(20) comment 'update author',
  primary key (id)
)

*database seller;
table t_order(
  order_id varchar(50) not null comment 'order id',
  chan_id varchar(50) not null comment 'channel id',
  product_id varchar(50) not null comment 'product id',
  chan_user_id varchar(50) not null comment 'channel user id',
  order_type varchar(50) not null comment 'type，APPLY：apply，REDEEM：redeem',
  order_status varchar(50) not null comment 'status',
  outer_order_id varchar(50) not null comment 'outer order id',
  amount decimal(15,3) not null comment 'amount',
  memo varchar(200) comment 'comment',
  create_at datetime comment 'create time',
  update_at datetime comment 'update time',
  primary key (order_id)
)

table verification_order(
  order_id varchar(50) not null comment 'order id',
  chan_id varchar(50) not null comment 'channel id',
  product_id varchar(50) not null comment 'product id',
  chan_user_id varchar(50) not null comment 'channel user id',
  order_type varchar(50) not null comment 'type，APPLY：apply，REDEEM：redeem',
  outer_order_id varchar(50) not null comment 'outer order id',
  amount decimal(15,3) not null comment 'amount',
  create_at datetime comment 'create time',
  primary key (order_id)
)
```
# REST Module Functionality
| Fucntion               | Restful API   | url             | JPA Repository           |
| ---------------------- | ------------- |-----------------| ------------------------ |
| Add product            | POST          | /pruducts       | JpaRepository            |
| Search single product  | GET           | /pruducts/{id}  | JpaSpecificationExecutor |
| Search multi products  | GET           | /products       | JpaSpecificationExecutor |

# Cache Design
| Default Method                                               | Customized Method                 |
| ------------------------------------------------------------ | --------------------------------- |
| If no parameter, take 0 as key                               | (#parameter#product.id)           | 
| If single parameter, take the parameter as key               | (#parameter_index#parameter0.id)  |
| If multi parameters, take the hashcode of parameters as key  |                                   |
* take product search in mem cache, as it is heavy duty search
* when the prodcut status changes, for example, from authorizating to published to sell, the cache memory of seller module will add this product. And vice versa.
* @Cacheable  
* CachePut
* CacheEvict
* value = cacheNames  
* condition  
* key  

# Message Passing
* VirtualTopic
* Workflow
![alt text](https://github.com/nickee1942/finance-demo/blob/main/message.jpeg)




