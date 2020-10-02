package com.nick.seller.repositorybackup;

import com.nick.entity.VerificationOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;


public interface VerifyRepository extends JpaRepository<VerificationOrder,String>,JpaSpecificationExecutor<VerificationOrder> {

    @Query(value = "select CONCAT_WS('|',order_id,outer_order_id,chan_id,chan_user_id,product_id,order_type,amount,DATE_FORMAT(create_at,'%Y-%m-%d %H:%i:%s'))\n" +
            "from t_order where order_status='success' and chan_id=?1 and create_at>=?2 and create_at<?3",nativeQuery = true)
    List<String> queryVerificationOrders(String chanId, Date start,Date end);

    @Query(value = "select t.order_id from t_order t left join verification_order v on t.chan_id=?1 and t.outer_order_id=v.order_id where v.order_id is null\n" +
            "and t.create_at>=?2 and t.create_at<?3",nativeQuery = true)
    List<String> queryExcessOrders(String chanId, Date start,Date end);

    @Query(value = "select v.order_id from verification_order v left join t_order t on t.chan_id=?1 and v.outer_order_id=t.order_id where t.order_id is null\n" +
            "and v.create_at>=?2 and v.create_at<?3",nativeQuery = true)
    List<String> queryMissOrders(String chanId, Date start,Date end);

    @Query(value = "select t.order_id from t_order t join verification_order v on t.chan_id=?1 and t.outer_order_id=v.order_id\n" +
            "where CONCAT_WS('|',t.chan_id,t.chan_user_id,t.product_id,t.order_type,t.amount,DATE_FORMAT(t.create_at,'%Y-%m-%d %H:%i:%s')) !=\n" +
            "CONCAT_WS('|',v.chan_id,v.chan_user_id,v.product_id,v.order_type,v.amount,DATE_FORMAT(v.create_at,'%Y-%m-%d %H:%i:%s'))\n" +
            "and t.create_at>=?2 and t.create_at<?3",nativeQuery = true)
    List<String> queryDifferentOrders(String chanId, Date start,Date end);
}
