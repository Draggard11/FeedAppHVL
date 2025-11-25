export const getPolls = async () => {
    try {
        const response = await fetch('http://localhost:8080/polls', {
            method: 'GET',
            credentials: 'include',
            headers: { 'Accept': 'application/json' }
        });

        if (!response.ok) {
            let msg = 'could not get polls from the server'
            try { msg = (await response.json()).message || msg; } catch {}
            throw new Error(msg);
        }
        
        return await response.json()
    } catch(error) {
        throw new Error(error.message || 'could not get polls from the server');
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

export const postVote = async (pollId, optionIndex, userID) => {
  try {
    const url = `http://localhost:8080/polls/${encodeURIComponent(
      pollId
    )}/votes?option=${encodeURIComponent(optionIndex)}&userId=${encodeURIComponent(userID)}`;

    const response = await fetch(url, {
      method: 'POST',
      credentials: 'include',
      headers: { Accept: 'application/json' }
    });

    if (!response.ok) {
      let msg = 'could not submit vote';
      try { msg = (await response.json()).message || msg; } catch {
        try { msg = await response.text() || msg; } catch {}
      }
      throw new Error(msg);
    }

    return true; 
  } catch (error) {
    throw new Error(error.message || 'could not submit vote');
  }
};

