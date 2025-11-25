<script>
    import '../app.css'
    import {logout} from '../services/auth.js'
    import {getPolls} from '../services/polls.js'
    import { userId } from '../userStore'
    import { onMount } from 'svelte';

    let polls = []
    let isLoading = false

    onMount(async () => {
        message = '';
        isLoading = true;
        try {
            const res = await getPolls();
            polls = res;
        } catch (err) {
            polls = []
            console.error(err);
        }
        isLoading = false;
    });
</script>

<nav>
    <a href="/">home</a>
    <a href="/login">login</a>
    <a href="/register">register</a>
    <a href="/polls">polls</a>
    <button on:click={logout}>logout</button>
</nav>

<slot />

{#if isLoading}
    <b>"loading..."</b>
{/if}


{#if polls?.length}
    <ol>
        {#each polls as p}
        <li>{p.question}</li>
        {/each}
    </ol>
{/if}

<style>
    nav button {
        position: absolute;
        top: 10px;
        right: 10px;
    }
</style>