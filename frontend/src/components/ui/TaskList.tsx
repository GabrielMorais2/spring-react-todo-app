import { Task } from '../../model/Task';
import TaskCard from './TaskCard';

interface TaskListProps {
    tasks: Task[];
    onUpdate: (task: Task) => void;
    onDelete: (taskId: number) => void;
}

const TaskList: React.FC<TaskListProps> = ({ tasks, onUpdate, onDelete }) => {
    return (
        <ul>
            {tasks.map((task) => (
                <TaskCard task={task} onUpdate={onUpdate} onDelete={onDelete} />
            ))}
        </ul>
    );
};

export default TaskList;