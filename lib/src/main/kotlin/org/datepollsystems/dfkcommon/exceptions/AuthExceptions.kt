package org.datepollsystems.dfkcommon.exceptions

class CredentialsIncorrectException :
    ForbiddenException("Credentials incorrect", "Credentials_incorrect")

class AccountNotActivatedException : ForbiddenException("Account not activated", "account_not_activated")

class PasswordChangeRequiredException : ForbiddenException("Password change required", "password_change_required")

class SessionTokenIncorrectException : ForbiddenException("Session token incorrect", "session_token_incorrect")

class NoPasswordChangeRequiredException :
    ForbiddenException("No password change required", "no_password_change_required")

class AuthEntityNotFoundException : UnauthorizedException("Auth entity not found")