<script>
  import CreatePoll from './Create.svelte';
  import Vote from './Vote.svelte';

  let tab = 'vote'; // 'vote' | 'create'
</script>

<h1>Polls</h1>

<nav style="margin-bottom:1rem;">
  <button type="button" on:click={() => (tab = 'vote')}   disabled={tab==='vote'}>Vote</button>
  <button type="button" on:click={() => (tab = 'create')} disabled={tab==='create'}>Create</button>
</nav>

{#if tab === 'vote'}
  <Vote />
{/if}
{#if tab === 'create'}
  <CreatePoll />
{/if}