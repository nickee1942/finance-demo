package com.nick.seller.service;

import com.nick.entity.VerificationOrder;
import com.nick.seller.enums.ChanEnum;
import com.nick.seller.repositorybackup.VerifyRepository;
import org.aspectj.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class VerifyService {

    @Autowired
    private VerifyRepository verifyRepository;

    @Value("${verification.rootdir:/opt/verification}")
    private String rootDir;

    private static String END_LINE = System.getProperty("line.separator","\n");
    private static DateFormat DAY_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private static DateFormat DATETIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    public File makeVerificationFile(String chanId, Date day){
        File path = getPath(rootDir,chanId,day);
        if(path.exists()){
            return path;
        }
        try {
            path.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Date start = getStartOfDay(day);
        Date end = add24Hours(start);
        List<String> orders = verifyRepository.queryVerificationOrders(chanId,start,end);
        String content = String.join(END_LINE,orders);
        FileUtil.writeAsString(path,content);
        return path ;
    }

    private Date add24Hours(Date start) {
        return new Date(start.getTime()+1000*60*60*24);
    }

    private Date getStartOfDay(Date day) {
        String start_str = DAY_FORMAT.format(day);
        Date start = null;
        try {
            start = DAY_FORMAT.parse(start_str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return start;
    }

    public File getPath(String rootDir,String chanId,Date day){
        String name = DAY_FORMAT.format(day)+"-"+chanId+".txt";
        File path = Paths.get(rootDir,name).toFile();
        return path;
    }

    public static VerificationOrder parseLine(String line){  //使用不到内部的数据
        VerificationOrder order = new VerificationOrder();
        String[] props = line.split("\\|"); //转义
        order.setOrderId(props[0]);
        order.setOuterOrderId(props[1]);
        order.setChanId(props[2]);
        order.setChanUserId(props[3]);
        order.setProductId(props[4]);
        order.setOrderType(props[5]);
        order.setAmount(new BigDecimal(props[6]));
        try {
            order.setCreateAt(DATETIME_FORMAT.parse(props[7]));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return order;
    }

    public void saveChanOrders(String chanId,Date day){
        ChanEnum conf = ChanEnum.getByChanId(chanId);
        File file = getPath(conf.getRootDir(),chanId,day);
        if(!file.exists()){
            return;
        }
        String content = null;
        try {
            content = FileUtil.readAsString(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[] lines = content.split(END_LINE);
        List<VerificationOrder> orders = new ArrayList<>();
        for(String line : lines){
            orders.add(parseLine(line));
        }
        verifyRepository.save(orders);
    }


    public List<String> verifyOrder(String chanId,Date day){
        List<String> errors = new ArrayList<>();
        Date start = getStartOfDay(day);
        Date end = add24Hours(start);
        List<String> excessOrders = verifyRepository.queryExcessOrders(chanId,start,end);
        List<String> missOrders = verifyRepository.queryMissOrders(chanId,start,end);
        List<String> differentOrders = verifyRepository.queryDifferentOrders(chanId,start,end);
        errors.add("excess order："+String.join(",",excessOrders));
        errors.add("missed order："+String.join(",",missOrders));
        errors.add("unmatchable order："+String.join(",",differentOrders));
        return errors;
    }
}
