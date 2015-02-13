/*
 * Copyright (c) 2005-2014, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.mb.integration.tests.amqp.functional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.wso2.mb.integration.common.clients.AndesClient;
import org.wso2.mb.integration.common.clients.operations.utils.AndesClientUtils;


/**
 * 1. subscribe to a single queue which will take 1/5 messages of sent and stop
 * 2. send messages to the queue
 * 3. close and resubscribe 5 times to the queue
 * 4. verify message count is equal to the sent total
 */
public class QueueSubscriptionsBreakAndReceiveTestCase {

    private static final Log log = LogFactory.getLog(QueueSubscriptionsBreakAndReceiveTestCase.class);

    @BeforeClass
    public void prepare() {
        AndesClientUtils.sleepForInterval(15000);
    }

    @Test(groups = {"wso2.mb", "queue"})
    public void performQueueSubscriptionsBreakAndReceiveTestCase() {

        Integer sendCount = 1000;
        Integer runTime = 40;
        int numberOfSubscriptionBreaks = 5;
        Integer expectedCount = sendCount / numberOfSubscriptionBreaks;

        AndesClient receivingClient = new AndesClient("receive", "127.0.0.1:5672", "queue:breakSubscriberQueue",
                "100", "false", runTime.toString(), expectedCount.toString(),
                "1", "listener=true,ackMode=1,delayBetweenMsg=0,stopAfter=" + expectedCount, "");

        receivingClient.startWorking();

        AndesClient sendingClient = new AndesClient("send", "127.0.0.1:5672", "queue:breakSubscriberQueue", "100",
                "false",
                runTime.toString(), sendCount.toString(), "1",
                "ackMode=1,delayBetweenMsg=0,stopAfter=" + sendCount, "");

        sendingClient.startWorking();

        boolean success = AndesClientUtils.waitUntilMessagesAreReceived(receivingClient, expectedCount, runTime);

        Assert.assertTrue(success, "Message receiving failed.");

        int totalMsgCountReceived = receivingClient.getReceivedqueueMessagecount();

        success = AndesClientUtils.waitUntilMessagesAreReceived(receivingClient, expectedCount, runTime);

        Assert.assertTrue(success, "Message receiving failed.");

        totalMsgCountReceived += receivingClient.getReceivedqueueMessagecount();

        success = AndesClientUtils.waitUntilMessagesAreReceived(receivingClient, expectedCount, runTime);

        Assert.assertTrue(success, "Message receiving failed.");

        totalMsgCountReceived += receivingClient.getReceivedqueueMessagecount();

        success = AndesClientUtils.waitUntilMessagesAreReceived(receivingClient, expectedCount, runTime);

        Assert.assertTrue(success, "Message receiving failed.");

        totalMsgCountReceived += receivingClient.getReceivedqueueMessagecount();

        success = AndesClientUtils.waitUntilMessagesAreReceived(receivingClient, expectedCount, runTime);

        Assert.assertTrue(success, "Message receiving failed.");

        totalMsgCountReceived += receivingClient.getReceivedqueueMessagecount();

       /* //anyway wait one more iteration to verify no more messages are delivered
        for (int count = 1; count < numberOfSubscriptionBreaks; count++) {

            receivingClient.startWorking();
            AndesClientUtils.waitUntilMessagesAreReceived(receivingClient, expectedCount, runTime);
            AndesClientUtils.sleepForInterval(2000);
            totalMsgCountReceived += receivingClient.getReceivedqueueMessagecount();
           // AndesClientUtils.sleepForInterval(1000);
        }*/

        Assert.assertEquals(totalMsgCountReceived, sendCount.intValue(), "Expected message count was not received.");

       log.info(totalMsgCountReceived);
    }

}
