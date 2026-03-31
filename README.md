# 🚨 SGMMS – Mobility & Security Management System (Simulation)

A simulation-based system developed in JavaFX that models urban mobility and security management through an interactive environment, applying data structures, algorithms, and real-time decision-making.

---

## 📌 Overview

SGMMS is a prototype designed to address real-world mobility and security challenges in Palmira, Colombia.

The system was implemented as an **interactive simulation**, where the user takes the role of a city operator responsible for monitoring incidents and coordinating emergency responses.

---

## 🎮 System Functionality

### 👤 Player Interaction

- The user controls a character that can move across the city map  
- Movement is handled using keyboard controls (W, A, S, D)  
- The player explores the environment and detects incidents in real time  

---

### 🚨 Incident Management

The system generates different types of incidents:

- 🔥 Fires  
- 🚓 Robberies  
- 💥 Traffic accidents  

Each incident contains:

- Location (coordinates)  
- Severity level  
- Description  
- Current status  

Incidents can appear dynamically or be accessed through a reporting panel.

---

### 🧠 Decision System

The user must assign the correct emergency vehicle to each incident:

- Ambulance → medical situations  
- Fire truck → fires  
- Police → robberies  

✔ Correct assignment → increases score  
❌ Incorrect assignment → no reward  

This mechanic simulates decision-making under real-time conditions.

---

### 📊 Incident Panel

- Displays all active incidents  
- Shows key information such as severity and location  
- Allows monitoring of system state  

---

## 🗺️ Interface (JavaFX)

The graphical interface was built using **JavaFX**, including:

- Map visualization  
- Character rendering  
- Incident display  
- Interactive panels  

---

## 🧱 Data Structures

The system was implemented using custom structures:

- Linked Lists (data storage)  
- Trees (organization of incidents)  
- Queues / Priority logic (incident handling)  

---

## ⚙️ Algorithms

- Sorting (based on priority or time)  
- Searching (by ID or attributes)  
- Decision validation logic  

---

## 📸 Screenshots

### 🟦 Map Interface
<img width="837" height="641" alt="image" src="https://github.com/user-attachments/assets/d9fbf60d-f965-4f0c-8834-b0602cc90e25" />


### 🚨 Incident Panel
<img width="960" height="745" alt="image" src="https://github.com/user-attachments/assets/07ed0b27-0650-47c3-842b-bc89c530ce0e" />


### 🎮 Gameplay
<img width="939" height="621" alt="image" src="https://github.com/user-attachments/assets/d3fe550a-e5a2-440b-acc2-dc562c979512" />


### ℹ️ Information Panel
<img width="982" height="758" alt="image" src="https://github.com/user-attachments/assets/de7afc65-2301-4ea1-8c6b-c6f7a628a5d7" />

---

## 🧪 Error Handling

- Custom exceptions implemented  
- Input validation  
- Controlled execution of actions  

---

## 🧠 Learning Outcomes

- Application of data structures in real scenarios  
- Integration of algorithms with interactive systems  
- Development of graphical interfaces with JavaFX  
- Simulation of real-world decision systems  

---

## 👤 Author

Andrés Felipe Rivas Ospina 
Isabella Londoño Lerma
Fredy Alejandro Cifuentes


Universidad Icesi
