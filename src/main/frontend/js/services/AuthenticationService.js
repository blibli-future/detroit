const _singleton = Symbol();
const TOKEN_KEY = 'token';
const EMAIL = 'email';

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
        localStorage.setItem(EMAIL, email);
      }
      return valid;
    });
  }

  redirectIfNotAuthenticated() {
    let token = localStorage.getItem(TOKEN_KEY);
    if (token === undefined || token === null || token === '') {
      window.location.assign("/view/login?error=NO_AUTH");
    }
    this._checkValidCredential(token).then((valid) => {
      if (!valid) {
        window.location.assign("/view/login?error=WRONG_AUTH");
      }
    });
  }

  apiCall(url, object) {
    this.redirectIfNotAuthenticated();
    if(!object.headers) {
      object.headers = {
        'Authorization': 'Basic ' + localStorage.getItem(TOKEN_KEY),
        "Content-Type": "application/json",
      }
    } else if(object.headers && !object.headers['Content-Type']) {
      object.headers['Authorization'] = 'Basic ' + localStorage.getItem(TOKEN_KEY);
      // object.headers['Content-Type'] = "application/json";
    } else if(object.headers && object.headers['Content-Type']) {
      object.headers['Authorization'] = 'Basic ' + localStorage.getItem(TOKEN_KEY);
    }
    return fetch(url, object);
  }

  async isLoggedIn() {
    let token = localStorage.getItem(TOKEN_KEY);
    if (token === undefined || token === null || token === '') {
      return false;
    }
    return await this._checkValidCredential(token).then((valid) => {
      return valid;
    });
  }

  getEmail() {
    return localStorage.getItem(EMAIL);
  }

  logout() {
    localStorage.clear();
    window.location.assign("/view/login?error=LOGOUT");
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
