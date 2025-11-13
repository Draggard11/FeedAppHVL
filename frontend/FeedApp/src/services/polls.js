export const getPolls = async () => {
    try {
        const response = await fetch('http://localhost:8080/polls', {
            method: 'GET',
            credentials: 'include',
            headers: { 'Content-Type': 'application/json' }
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


export const postPoll = async (userID, question, options) => {
    try {
        const response = await fetch('http://localhost:8080/polls', {
            method: 'POST',
            credentials: 'include',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({
                createdBy: userID,
                question: question,
                options: options
            }),
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

