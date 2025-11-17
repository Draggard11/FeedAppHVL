<script>
    import { postPoll } from '../../services/polls.js';
    import { userId } from '../../userStore.js';

    let question = '';
    let optionCaptions = ['', ''];
    let status = '';
    let saving = false;

    function addOption() {
        optionCaptions = [...optionCaptions, ''];
    }

    function reset() {
        question = '';
        optionCaptions = ['', ''];
        status = '';
    }

    async function submit() {
        const clean = optionCaptions.map((s) => s.trim()).filter(Boolean);
        if (!question.trim() || clean.length < 2) {
        status = 'Needs a question and at least 2 options';
        return;
        }
        if (!$userId) {
        status = 'You must be logged in.';
        return;
        }

        saving = true;
        status = 'Saving…';
        try {
        await postPoll(Number($userId), question.trim(), clean);
        reset();
        status = 'Created poll!';
        } catch (error) {
        status = error.message || 'Failed to create poll';
        } finally {
        saving = false;
        }
    }
</script>

<div class="card box">
    <h2>Create Poll</h2>

    <div class="field">
        <input class="input" placeholder="Question" bind:value={question} />
    </div>

    {#each optionCaptions as _, i}
        <div class="field">
        <input class="input" placeholder={`Option ${i + 1}`} bind:value={optionCaptions[i]} />
        </div>
    {/each}

    <div class="buttonField">
        <button class="btn reset" type="button" on:click={reset}>Reset form</button>
        <button class="btn white" type="button" on:click={addOption}>Add option</button>
        <button class="btn save" type="button" on:click={submit} disabled={saving}>
        {saving ? 'Saving…' : 'Save'}
        </button>
    </div>
</div>

{#if status}<p class="status">{status}</p>{/if}
