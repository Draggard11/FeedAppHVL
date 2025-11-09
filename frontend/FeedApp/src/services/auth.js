export const login = async (username, email, password) => {
    const response = await fetch('http://localhost:8080/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: { username: username, email: email, password: password },
        credentials: 'include',
    });

    return await response.json();
}

export const register = async (username, email, password) => {
    const response = await fetch('http://localhost:8080/register', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: { username: username, email: email, password: password },
        credentials: 'include',
    });

    return await response.json();
}