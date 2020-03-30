package com.rock.service.common;

import com.alibaba.otter.canal.protocol.CanalEntry;
import com.alibaba.otter.canal.protocol.Message;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

/**
 * Created by rock on 2019/11/5.
 * kafka的消费者
 */
@Component
@Slf4j
public class KafKaMktConsumer {

    /**
     * 消费者一旦启动，就会监听kafka服务器上的topic，实时进行消费消息。
     * 当然，我们可以在该类中定义多个不同的方法，并都在方法上使用 @KafkaListener ，为它指定不同的topic及分区信息，这样每个方法就相当于一个消费者了。
     * @KafkaListener 如果指定 groupId = "hj_mkt_test_groupId1"会覆盖配置文件中使用的默认配置：spring.kafka.consumer.group-id=testGroup
     * @param message
     */
    @KafkaListener(topics = {"mkt_leads_ext"}, groupId = "testGroup100")
    public void onMessage(Message message){
        if(Objects.isNull(message)){
            log.error("message is null");
            return;
        }
//        ConsumerConfig.MAX_POLL_RECORDS_CONFIG
        log.info("###########KafKaMktConsumer:message###########");
        printEntry(message.getEntries());
    }



    private static void printEntry(List<CanalEntry.Entry> entrys) {
        for (CanalEntry.Entry entry : entrys) {
            if (entry.getEntryType() == CanalEntry.EntryType.TRANSACTIONBEGIN
                    || entry.getEntryType() == CanalEntry.EntryType.TRANSACTIONEND) {
                continue;
            }

            CanalEntry.RowChange rowChage;
            try {
                rowChage = CanalEntry.RowChange.parseFrom(entry.getStoreValue());
            } catch (Exception e) {
                throw new RuntimeException("ERROR ## parser of eromanga-event has an error , data:" + entry.toString(), e);
            }

            CanalEntry.EventType eventType = rowChage.getEventType();
            System.out.println(String.format("================> binlog[%s:%s] , name[%s,%s] , eventType : %s",
                    entry.getHeader().getLogfileName(), entry.getHeader().getLogfileOffset(),
                    entry.getHeader().getSchemaName(), entry.getHeader().getTableName(), eventType));

            for (CanalEntry.RowData rowData : rowChage.getRowDatasList()) {
                if (eventType == CanalEntry.EventType.DELETE) {
                    printColumn(rowData.getBeforeColumnsList());
                } else if (eventType == CanalEntry.EventType.INSERT) {
                    printColumn(rowData.getAfterColumnsList());
                } else {
                    System.out.println("-------> before");
                    printColumn(rowData.getBeforeColumnsList());
                    System.out.println("-------> after");
                    printColumn(rowData.getAfterColumnsList());
                }
            }
        }
    }

    private static void printColumn(List<CanalEntry.Column> columns) {
        for (CanalEntry.Column column : columns) {
            System.out.println(column.getName() + " : " + column.getValue() + "    update=" + column.getUpdated());
        }
    }

}
