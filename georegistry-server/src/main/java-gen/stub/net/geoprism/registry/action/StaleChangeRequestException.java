package net.geoprism.registry.action;

public class StaleChangeRequestException extends StaleChangeRequestExceptionBase
{
  private static final long serialVersionUID = -245372448;
  
  public StaleChangeRequestException()
  {
    super();
  }
  
  public StaleChangeRequestException(java.lang.String developerMessage)
  {
    super(developerMessage);
  }
  
  public StaleChangeRequestException(java.lang.String developerMessage, java.lang.Throwable cause)
  {
    super(developerMessage, cause);
  }
  
  public StaleChangeRequestException(java.lang.Throwable cause)
  {
    super(cause);
  }
  
}
