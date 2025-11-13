<script>
    import {postPoll} from '../../services/polls.js'
    import {userId} from '../../userStore.js'

    let question = "";
    let options = [""];
    let message = "";
    let isLoading = false;

    const handlePollCreation = async (event) => {
        event.preventDefault();
        isLoading = true;

        try {
            let response = await postPoll(userId, question, options);
            message = response.message;
        } catch(error) {
            message = error.message;
        } finally {
            isLoading = false;
        }
    };
</script>

<h1>create poll</h1>
<form on:submit|preventDefault={handlePollCreation}>
    <input type="text" placeholder="Poll Question" bind:value={question} required />
    {#each options as option, index}
        <input type="text" placeholder={`Option ${index + 1}`} bind:value={options[index]} required />
    {/each}
    <button type="submit" >{isLoading ? "registering..." : "register"}</button>
</form>