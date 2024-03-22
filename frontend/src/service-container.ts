import TaskService from "./services/TaskService";

class ServiceContainer {
    private static instance: ServiceContainer;
    private taskService: TaskService;

    private constructor() {
        this.taskService = new TaskService(import.meta.env.VITE_API_URL as string);
    }

    static getInstance(): ServiceContainer {
        if (!ServiceContainer.instance) {
            ServiceContainer.instance = new ServiceContainer();
        }
        return ServiceContainer.instance;
    }

    getTaskService(): TaskService {
        return this.taskService;
    }
}

export default ServiceContainer;
