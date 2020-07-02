/**
 * Copyright (c) 2019 TerraFrame, Inc. All rights reserved.
 *
 * This file is part of Geoprism Registry(tm).
 *
 * Geoprism Registry(tm) is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * Geoprism Registry(tm) is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with Geoprism Registry(tm).  If not, see <http://www.gnu.org/licenses/>.
 */
package net.geoprism.dhis2.dhis2adapter.response;

import java.util.List;

import com.google.gson.JsonObject;

import net.geoprism.dhis2.dhis2adapter.response.model.ErrorReport;

public class DHIS2ImportResponse extends DHIS2Response
{
  public DHIS2ImportResponse(String response, int statusCode)
  {
    super(response, statusCode);
  }
  
  public DHIS2ImportResponse(DHIS2Response http)
  {
    super(http.getResponse(), http.getStatusCode());
  }
  
  public boolean isSuccess()
  {
    if (this.response == null)
    {
      return super.isSuccess();
    }
    
    return super.isSuccess() && !this.getJsonObject().get("status").getAsString().equals("ERROR");
  }
  
  public String getMessage()
  {
//    {"httpStatus":"Conflict","httpStatusCode":409,"status":"ERROR","message":"Unexpected end-of-input: expected close marker for Array (start marker at [Source: (BufferedInputStream); line: 1, column: 22])\n at [Source: (BufferedInputStream); line: 1, column: 45]"}

    if (this.hasMessage())
    {
      return this.getJsonObject().get("message").getAsString();
    }
    else
    {
      return "";
    }
  }
  
  public boolean hasMessage()
  {
    JsonObject jo = this.getJsonObject();
    
    return jo != null && jo.has("message") && jo.get("message").getAsString().length() > 0;
  }
  
  public Boolean hasErrorReports()
  {
    return false;
  }
  
  public List<ErrorReport> getErrorReports()
  {
    return null;
  }
}
