#!/usr/bin/env bash

set -euo pipefail

repository_root="$(git rev-parse --show-toplevel)"
cd "$repository_root"

if ! command -v act >/dev/null 2>&1; then
  echo "ERROR: act is required before pushing. Install nektos/act and Docker." >&2
  exit 1
fi

if ! docker info >/dev/null 2>&1; then
  echo "ERROR: Docker must be running before pushing because act executes the PR workflow locally." >&2
  exit 1
fi

echo "Running every pull-request workflow locally before push..."
temporary_worktree="$(mktemp -d "${TMPDIR:-/tmp}/payguard-pre-push.XXXXXX")"

cleanup() {
  git worktree remove --force "$temporary_worktree" >/dev/null 2>&1 || true
}

trap cleanup EXIT INT TERM

git worktree add --detach "$temporary_worktree" HEAD >/dev/null
cd "$temporary_worktree"
act pull_request --container-architecture linux/amd64 --no-cache-server
