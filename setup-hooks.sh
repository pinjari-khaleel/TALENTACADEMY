#!/bin/bash
ln -s -f ../../hooks/pre-commit .git/hooks/pre-commit
ln -s -f ../../hooks/commit-msg .git/hooks/commit-msg
echo "Symlinks created successflly"
 
