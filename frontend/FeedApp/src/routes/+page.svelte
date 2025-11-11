<script>
    import {getPolls} from '../services/polls.js'
    import { onMount } from 'svelte';

    let polls = []
    let isLoading = false


    onMount(async (event) => {

        isLoading = true;
        try {
            let response = await getPolls();
            polls = response.message;
        } catch (error) {
            polls = error.message;
        }
        isLoading = false;
    });

</script>

<h1>Welcome to SvelteKit</h1>
<p>Visit to read the documentation</p>


{#if isLoading}
    <b>"loading..."</b>
{/if}

{#if polls !== null && polls.length > 0}
    <ol>{polls}</ol>
{/if}

<b>{polls}</b>
