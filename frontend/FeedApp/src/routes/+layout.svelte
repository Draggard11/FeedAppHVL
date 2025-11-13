<script>
  import '../app.css'
  import {logout} from '../services/auth.js'
  import {getPolls} from '../services/polls.js'
  import { userId } from '../userStore'
  import {get} from 'svelte/store'
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

<nav>
  <a href="/">home</a>
  <a href="/login">login</a>
  <a href="/register">register</a>
  <a href="/polls">polls</a>
  <button onClick={logout}>logout</button>
</nav>

<slot />

{#if isLoading}
    <b>"loading..."</b>
{/if}
<b>{get(userId)}</b>
{#if polls !== null && polls.length > 0}
    <ol>{polls}</ol>
{/if}


<style>
    nav button {
        position: absolute;
        top: 10px;
        right: 10px;
    }
</style>