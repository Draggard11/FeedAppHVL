// javascript
export const login = async (username, email, password) => {
    const response = await fetch('http://localhost:8080/login', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ username, email, password }),
        credentials: 'include'
    });

    if (!response.ok) throw new Error(`HTTP ${response.status}`);
    return await response.json();
};

export const register = async (username, email, password) => {
    const response = await fetch('http://localhost:8080/register', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ username, email, password }),
        credentials: 'include'
    });

    if (!response.ok) throw new Error(`HTTP ${response.status}`);
    return await response.json();
};

export const logout = async () => {
    const response = await fetch('https://localhost:8080/logout', {
        method: 'POST',
        credentials: 'include'
    });

    if (!response.ok) throw new Error(`HTTP ${response.status}`);
    return await response.json();
}
