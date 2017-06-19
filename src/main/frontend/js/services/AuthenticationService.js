const _singleton = Symbol();
const TOKEN_KEY = 'token';

class AuthenticationService {
  constructor(singletonToken) {
    if(singletonToken !== _singleton) {
      throw new Error('Cannot construct singleton');
    }
  }

  static get instance() {
    if(!this[_singleton])
      this[_singleton] = new AuthenticationService(_singleton);

    return this[_singleton];
  }

  authenticate(email, password) {
    let token = btoa(email + ':' + password);
    return this._checkValidCredential(token).then((valid) => {
      if (valid) {
        localStorage.setItem(TOKEN_KEY, token);
      }
      return valid;
    });
  }

  redirectIfNotAuthenticated() {
    let token = localStorage.getItem(TOKEN_KEY);
    if (token === undefined) {
      window.location.assign("/view/login?error=NO_AUTH");
    }
    this._checkValidCredential(token).then((valid) => {
      if (!valid) {
        window.location.assign("/view/login?error=WRONG_AUTH");
      }
    });
  }

  _checkValidCredential(token) {
    return fetch('/api/v1/users/login', {
      method: 'POST',
      headers: {
        'Authorization': 'Basic ' + token,
      },
    }).then((response) => {
      return response.ok;
    });
  }
}

export default AuthenticationService;
