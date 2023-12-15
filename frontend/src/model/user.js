export default class User {
    constructor(username) {
        this.username = username;
    }

    getUsername() {
        return this.username;
    }

    setUsername(username) {
        this.username = username;
    }
}
