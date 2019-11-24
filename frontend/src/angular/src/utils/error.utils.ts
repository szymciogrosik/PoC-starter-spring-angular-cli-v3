export class ErrorUtils {
  // Error codes
  public static UNHANDLED_ERROR = 'F_ERR_000';
  public static HTTP_UNAUTHORIZED = 'F_ERR_001';

  // Error messages
  private static messages: Map<string, string> = new Map([
    [ ErrorUtils.HTTP_UNAUTHORIZED, 'Brak autoryzacji' ]
  ]);

  private static defaultMessage = 'Nieznany błąd';

  public static getMessage(errorCode: string): string {
    const message = ErrorUtils.messages.get(errorCode);
    return message !== undefined ? ErrorUtils.buildMessage(errorCode, message)
      : ErrorUtils.buildMessage(ErrorUtils.UNHANDLED_ERROR, ErrorUtils.defaultMessage);
  }

  private static buildMessage(errorCode: string, errorMessage: string): string {
    return 'Kod błędu: ' + errorCode + ' - ' + errorMessage + '.';
  }
}
