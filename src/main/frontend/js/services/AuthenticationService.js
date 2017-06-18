const _singleton = Symbol();

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
    token = btoa(email + ':' + password);
    _checkValidCredential(token).then((valid) => {
      if (valid) {
        localStorage.setItem('token', token);
      }
      return valid;
    });
  }

  isAuthenticated() {
    let token = localStorage.getItem('token');
    if (token === undefined) {
      return false
    }
    return token; // TODO maybe we need to always check against server
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
