/*
 * Copyright (c) 2016, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
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

package org.wso2.mb.migration;

/**
 * Class to represent the Bindings stored in the Database
 */
public class Binding {

    /**
     * The queue name for which the binding is bound to
     */
    private String queueName;

    /**
     * The exchange for which the binding is bound
     */
    private String exchangeName;

    /**
     * String representing the details of the binding
     */
    private String bindingDetails;

    public Binding(String queue, String exchange, String details){
        queueName =queue;
        exchangeName = exchange;
        bindingDetails = details;
    }

    public String getExchangeName() {
        return exchangeName;
    }

    public String getBindingDetails() {
        return bindingDetails;
    }

    public void setBindingDetails(String bindingDetails) {
        this.bindingDetails = bindingDetails;
    }

    public String getQueueName() {
        return queueName;
    }
}
