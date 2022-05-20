/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
 
package org.apache.hop.pipeline.transforms.dorisbulkloader;

public class LoadConstants {
    public static final String COLUMNS_KEY = "columns";
    public static final String JSON = "json";
    public static final String CSV = "csv";
    public static final String LABEL_KEY = "label";
    public static final String FORMAT_KEY = "format";
    public static final String FIELD_DELIMITER_KEY = "column_separator";
    public static final String FIELD_DELIMITER_DEFAULT = ",";
    public static final String LINE_DELIMITER_KEY = "line_delimiter";
    public static final String LINE_DELIMITER_DEFAULT = "\\n";
    public static final String LINE_DELIMITER_JSON = ",";
    public static final String EXCEPT_DEFAULT = "100-continue";
    public static final String STRIP_OUTER_ARRAY_KEY = "strip_outer_array";
    public static final String STRIP_OUTER_ARRAY_DEFAULT = "true";
    public static final String NULL_VALUE = "\\N";
    public static final String DORIS_DELETE_SIGN = "__DORIS_DELETE_SIGN__";
    public static final String LABEL_SUFFIX = "HopDorisBulkLoad";
    public static final String LOAD_URL_PATTERN = "http://%s:%s/api/%s/%s/_stream_load";
    public static final String JSON_ARRAY_START = "[";
    public static final String JSON_ARRAY_END = "]";
}
