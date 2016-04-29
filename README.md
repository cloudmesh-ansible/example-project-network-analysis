## Project Report: Construct Network Graphs using Python, Spark, and Hadoop
#### Final Project for I590 Big Data Software, Spring 2016

#### Student Name: Ji Ma

### Description of Project

This project will deploy Big Data Analytics Stack on virtual machines and then construct 100 graphs from 100 csv files using Python, Spark, and Hadoop.

### Problem Statement

Network analysis usually requires intensive computing resources and long time waiting; therefore, run the tasks using HDFS, Spark, and Python can greatly improve the efficiency.

### Major Software Packages and Technologies
1. Ansible: For automated deployment of software packages across multiple VMs and running script;
2. Hadoop: For hosting dataset;
3. Spark and Python: For constructing graphs and analysis purpose.

### Purpose and Objectives
This project will complete the following tasks:

1. Deploy the Big Data Stack following the official documents;
2. Use Ansible Playbook install Python packages (i.e., networkx and pandas) on VMs for network analysis;
3. Use Ansible Playbook download the 100 csv files (dataset hosted on my own website);
4. Use Ansible Playbook put the dataset onto HDFS;
5. Use Ansible Playbook download the python script for analysis (script wrote by myself and hosted on my own website);
6. Use Ansible Playbook run the analysis script.

Task #2-6 are wrote into Ansible Playbook `site.yml`.

### How to Run
1. Clone this repository.
2. Deployed the Big Data Stack following the official documents (https://github.com/futuresystems/big-data-stack);
3. Run `ansible-playbook addons/{pig,spark}.yml` to install the Pig and Spark addons.
4. Run `ansible-playbook site.yml` to down dataset, deploy onto HDFS, and run analysis et al (Major tasks of this project).

### Results
After the analysis (takes about 1 minute), 100 GraphML files can be obtained and stored under /tmp/graphs directory in the fronthead VM.

### Findings

Major finding is the improvement of efficiency: usually it takes about 20 minutes to complete a similar task, current project only takes about 1 minute.

### References & Acknowledgements
- Original dataset: http://jima-wordpress.stor.sinaapp.com/simu-0-99.zip
- Python analysis script: http://jima-wordpress.stor.sinaapp.com/graph_generator.py

Special thanks to Hyungro Lee and Badi' Abdul-Wahid for their patient guidance.
