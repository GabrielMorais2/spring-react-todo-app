import { Task } from '../model/Task';

class TaskService {
    private apiUrl: string;

    constructor(apiUrl: string) {
        this.apiUrl = apiUrl;
    }

    async updateTask(task: Task): Promise<Response> {
        const response = await fetch(`${this.apiUrl}/api/tasks/${task.id}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ ...task, completed: !task.completed }),
        });

        return response;
    }

    async updateTaskWithUpdatedTask(task: Task, editedDescription:string): Promise<Response> {
        const updatedTask = { ...task, description: editedDescription };
        const response = await fetch(`${this.apiUrl}/api/tasks/${task.id}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(updatedTask),
        });

        return response;
    }

    async createTask(newTask: Task): Promise<Response> {
        const response = await fetch(`${this.apiUrl}/api/tasks`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(newTask),
        });

        return response;
    }

    async getTasks(): Promise<Response> {
        try {
            const response = await fetch(this.apiUrl + '/api/tasks');
            if (response.status === 200) {
                const data = await response.json();
                return data;
            } else {
                console.error('Failed to fetch tasks');
            }
        } catch (error) {
            console.error('Error fetching tasks:', error);
        }
        return Promise.reject('Error fetching tasks');
    }

    async deleteTasksByCompletion(completed: boolean): Promise<Response> {
        const response = await fetch(`${this.apiUrl}/api/tasks/?completed=${completed}`, {
            method: 'DELETE',
        });

        return response;
    }
}

export default TaskService;