# Introduction

A Java Springboot service within Access to Work (AtW) that will facilitate and manage to submission of claims within AtW. We have a couple of points that you'll need to be aware of:

* If you find a security issue, please let us know directly by detailing the issue and emailing it to us at [SecureComms](mailto:secure.communicationsproject@dwp.gov.uk). We will then take a look at the issue. 
* If you wish to raise a pull request, please provide details of the change made and the reason why it was required.

## Submitting changes

Please send a GitHub Pull request to [SecureComms](mailto:secure.communicationsproject@dwp.gov.uk?Subject=Pull%20Request) with a clear list of changes made (read more about [pull requests](https://help.github.com/articles/about-pull-requests/)).  When you send a pull request, please include examples and use cases; we can always use more test coverage.

Please follow our coding conventions (below), make sure all of your commits are atomic (one feature per commit) and always write a clear message against your commit that fully describes the change.

    git commit -m "A summary of the commit, what’s changed and the impact."

## Coding conventions

We use [gitlab-sast](https://docs.gitlab.com/ee/user/application_security/sast/analyzers.html) for code quality as part of our CI pipelines. All code follows the standard out-of-the-box code formatting rules.

This is open source software and any code you contribute will be in the public domain so please ensure that not only is your code  efficient, but can be easily followed as well.

## Testing

We use [Cucumber](https://cucumber.io/) and [Selenium](https://www.selenium.dev/) to test our code.  Some of our samples haven't been included in the repository because they expose information about our internal processes than we cannot share.

## Communication

Once we have received a request, we will acknowledge that we have received it.

When we have assessed the content and the purpose of your suggestion, we will inform you of what will happen with the suggestion. However, please don't be offended if we are unable to accept your suggestion; we will try and explain the reasons for this decision if that's the case.

If we _can_ accept your contribution we'll raise a task on our backlog to implement the change.  Ultimately, your code may look a little different to the original submission once our standards, security concerns and internal formatting rules have been applied but it’s still your contribution.

If we accept your code and it makes it to a release then we'll acknowledge your valuable contribution in the change log, as well as letting you know directly.

_`document reference : ContributionGuide.docx (1.1)`_

### WARNING
The code within this GitHub repository will not compile or build due to service dependencies that are intentionally not available in the Maven central repository. For more information please contact [SecureComms](mailto:secure.communicationsproject@dwp.gsi.gov.uk?Subject=GitHub%20Repo%20%Will%20Not%20Build).