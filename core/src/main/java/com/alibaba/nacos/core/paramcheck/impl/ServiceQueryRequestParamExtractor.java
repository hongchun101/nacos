/*
 * Copyright 1999-2023 Alibaba Group Holding Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.alibaba.nacos.core.paramcheck.impl;

import com.alibaba.nacos.api.naming.remote.request.ServiceQueryRequest;
import com.alibaba.nacos.api.remote.request.Request;
import com.alibaba.nacos.common.paramcheck.ParamCheckUtils;
import com.alibaba.nacos.common.paramcheck.ParamInfo;
import com.alibaba.nacos.common.utils.StringUtils;
import com.alibaba.nacos.core.paramcheck.AbstractRpcParamExtractor;

/**
 * Param extractor for {@link ServiceQueryRequest}.
 *
 * @author zhuoguang
 */
public class ServiceQueryRequestParamExtractor extends AbstractRpcParamExtractor {

    @Override
    public void init() {
        addTargetRequest(ServiceQueryRequest.class.getSimpleName());
    }

    @Override
    public void extractParamAndCheck(Request request) throws Exception {
        ServiceQueryRequest req = (ServiceQueryRequest) request;
        ParamInfo paramInfo = new ParamInfo();
        paramInfo.setNamespaceId(req.getNamespace());
        paramInfo.setServiceName(req.getServiceName());
        paramInfo.setGroup(req.getGroupName());
        paramInfo.setPort(String.valueOf(req.getUdpPort()));
        ParamCheckUtils.checkParamInfoFormat(paramInfo);
        String clusterString = req.getCluster();
        if (StringUtils.isNotBlank(clusterString)) {
            String[] clusters = clusterString.split(",");
            for (String cluster : clusters) {
                ParamCheckUtils.checkClusterFormat(cluster);
            }
        }
    }
}
