export const login = async (username, email, password) => {
    try {
        const response = await fetch('http://localhost:8080/login', {
            method: 'POST',
            credentials: 'include',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(
                {username: username,
                    email: email,
                    password: password,
                }),
        });

        if (!response.ok) {
            const data = await response.json();
            throw new Error(data.message || "login failed");
        }
        const data = await response.json();
        return {
            message: `Logged in as ${data.username}`,
            user: data.username, // can be used later
            id: data.id,
        }
    } catch(error) {
        throw new Error(error.message || "login failed");
    }
};

export const register = async (username, email, password) => {
    try {
        const response = await fetch('http://localhost:8080/register', {
            method: 'POST',
            credentials: 'include',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(
                {username: username,
                    email: email,
                    password: password,
                }),
        });

        if (!response.ok) {
            const data = await response.json();
            throw new Error(data.message || "failed to register");
        }

        return await login(username, email, password)

    } catch(error) {
        throw new Error(error.message || "failed to register");
    }
};

export const logout = async () => {
    try {
        const response = await fetch('http://localhost:8080/logout', {
            method: 'POST',
            credentials: 'include'
        });

        if (!response.ok) {
            const data = await response.json();
            throw new Error(data.message || "logout failed");
        }
        const data = await response.json();
        return {
            message: `Logged out as ${data.username}`,
            user: data.username,
            id: data.id,
        }
    } catch(error) {
        throw new Error(error.message || "logout failed");
    }
}
