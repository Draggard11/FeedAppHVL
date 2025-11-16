<script>
    import {postPoll} from '../../services/polls.js'
    import {userId} from '../../userStore.js'

    let question = "";
    let options = [""];
    let message = "";
    let isLoading = false;

    const addOption = () => options = [...options, '']
    const handlePollCreation = async () => {
        isLoading = true;

        try {
            const response = await postPoll($userId, question, options);
            message = response.message;
            question = '';
            options = [''];
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
    <button type="button" on:click={addOption}>+ option</button>
    <button type="submit">{isLoading ? 'creating...' : 'create poll'}</button>
</form>

{#if message}
    <p>{message}</p>
{/if}