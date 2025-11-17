<script>
  import { onMount } from 'svelte';
  import { getPolls, postVote } from '../../services/polls.js';
  import { userId } from '../../userStore.js';
  import { error } from '@sveltejs/kit';

  let phase = 'pick';

  let polls = [];
  let status = '';

  let selectedPollId = null;
  $: selectedPoll = polls.find(p => p.id === selectedPollId);


  let loadingList = false;
  let submitting = false;

  onMount(loadPolls);

  async function loadPolls() {
    status = 'Loading…';
    loadingList = true;
    try {
      const data = await getPolls(); 
      polls = Array.isArray(data) ? data : [];
      if (polls.length === 0) status = 'No polls yet.';
      else status = '';
    } catch (error) {
      status = error.message || 'Failed to load polls';
    } finally {
      loadingList = false;
    }
  }

  function choose(id) {
    selectedPollId = id;
    phase = 'vote';
    status = '';
  }

  async function submitVote(optionIndex) {
    if (!selectedPollId && selectedPollId !== 0) return;
    if (!$userId) {
      status = 'Log in to vote.';
      return;
    }
    submitting = true;
    status = 'Submitting vote…';
    try {
      await postVote(selectedPollId, optionIndex, $userId);
      status = 'Vote recorded';

      await loadPolls();
    } catch (error) {
      status = error.message || 'Failed to submit vote';
    } finally {
      submitting = false;
    }
  }

  function back() {
    phase = 'pick';
    selectedPollId = null;
    status = '';
  }
</script>

{#if phase === 'pick'}
  <div class="card box">
    <h2>Select a poll</h2>

    <div class="list">
      {#if loadingList}
        <p>Loading…</p>
      {:else if polls.length === 0}
        <p>No polls yet.</p>
      {:else}
        {#each polls as p (p.id)}
          <button class="btn" on:click={() => choose(p.id)}>{p.question}</button>
        {/each}
      {/if}
    </div>

    <div class="actions">
      <button class="btn" on:click={loadPolls} disabled={loadingList}>
        {loadingList ? 'Refreshing…' : 'Refresh'}
      </button>
    </div>

    {#if status}<p class="status">{status}</p>{/if}
  </div>
{/if}

{#if phase === 'vote' && selectedPoll}
  <div class="card box">
    <h2>{selectedPoll.question}</h2>

    <div class="options">
      {#each (selectedPoll.pollOptions ?? []) as opt, idx (opt.id ?? idx)}
        <div class="option-row">
          <span>{opt.title ?? opt.caption ?? `Option ${idx + 1}`}</span>
          <button
            class="btn save"
            on:click={() => submitVote(idx)}
            disabled={submitting}
          >
            {submitting ? 'Submitting…' : 'Vote'}
          </button>
          <span> — {opt.voteCount ?? 0} {(opt.voteCount ?? 0) === 1 ? 'vote' : 'votes'}</span>
        </div>
      {/each}
    </div>

    <div class="actions">
      <button class="btn" on:click={back}>Go Back</button>
    </div>

    {#if status}<p class="status">{status}</p>{/if}
  </div>
{/if}
