export const getPolls = async () => {
    try {
        const response = await fetch('http://localhost:8080/polls', {
            method: 'GET',
            headers: { 'Content-Type': 'application/json' },
            credentials: 'include'
        });

        if (!response.ok) {
            const data = await response.json();
            throw new Error(data.message || "could not get polls from the server" );
        }
        const data = await response.json();
        return {
            message: data.message,
        }

    } catch (error) {
        throw new Error(error.message || "could not get polls from the server");
    }
};


export const postPoll = async (userID, question, option) => {
    try {
        const response = await fetch('http://localhost:8080/polls', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({userID, question, option}),
            credentials: 'include'
        });

        if (!response.ok) {
            const data = await response.json();
            throw new Error(data.message || "could not post poll");
        }

        const data = await response.json();
        return {
            message: data.message,
        }
    } catch (error) {
        throw new Error(error.message || "could not post poll");
    }
};

